;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.

(defn new-user [id login monthly-budget]
 {:id id
 :login login
 :monthly-budget monthly-budget
 :total-expenses 0})

;; Ref(erence) sync & coordinated
(def all-users (ref {}))


(deref all-users)
;; Syntactic sugar for deref
@all-users

;; We need dosync because refs can only be changed within an SMT transaction
(dosync
;; ref-set replaces the old value with a new one
 (ref-set all-users {:a 1}))

;; If you try it outside the SMT an exception is thrown
(ref-set all-users {})


;; Typically, a ref is mutated by using its current value, applying a function to it, and storing the new value back into it.
;; This read-process-write operation is a common scenario, and Clojure provides the alter function that can do this as an atomic operation.
;; (alter ref function & args)
(defn add-new-user [login budget-amount]
  (dosync
   ;; The let is included in dosync to avoid buried updates therefore ensuring the deref of all-users to be valid even if it's modified by other thread
   (let [current-number (count @all-users)
         user (new-user (inc current-number) login budget-amount)]
     (alter all-users assoc login user))))


;; (commute ref function & args)

;; The commute function is useful where the order of the function application isn’t important.
;; For instance, imagine that a number was being incremented inside a transaction. If two threads were to go at it in parallel,
;; at the end of the two transactions, it wouldn’t matter which thread had committed first. The result would be that the number was incremented twice.
;; When the alter function is applied, it checks to see if the value of the ref has changed because of another committed transaction. This causes the current transaction to fail and for it to be retried.
;; The commute function doesn’t behave this way; instead, execution proceeds forward and all calls to commute are handled at the end of the transaction
;; As explained earlier, the function passed to commute should be commutative.

