;; Atoms
;; Clojure provides a special construct in atom that allows for synchronous and independent changes to mutable data.
;; The difference between an atom and an agent is that updates to agents happen asynchronously at some point in the future, whereas atoms are updated synchronously (immediately).
;; Atoms differ from refs in that changes to atoms are independent from each other and can’t be coordinated so that they either all happen or none do.
;; Atoms can be used whenever there’s need for some state but not for coordination with any other state.
(def total-rows (atom 0))

(deref total-rows)
@total-rows


;; Mutating atoms

;; There’s an important difference between atoms and refs, in that changes to one atom are independent of changes to other atoms.
;; Therefore, there’s no need to use transactions when attempting to update atoms.

;; The reset!  function doesn’t use the existing value of the atom and sets the provided value as the new value of the atom.
(reset! atom new-value)

;; swap!
;; If the atom is modified prior to the swap! finishing it's execution swap! will retry the operation with the new value for the  atom
(swap! the-atom the-function & more-args)
(swap! total-rows + 100) ;; 100


;; compare-and-set!

;; This function atomically sets the value of the atom to the new value, if the current value of the atom is equal to the supplied old value.
;; If the operation succeeds, it returns true; else it returns false.
;; A typical workflow of using this function is to dereference the atom in the beginning, do something with the value of the atom, and then use compare-and-set!  to change the value to a new one.
;; If another thread had changed the value in the meantime (after it had been dereferenced), then the mutation would fail.
;; The swap!  function does that internally, the swap!  function will reapply the mutation function until it succeeds.
(compare-and-set! the-atom old-value new-value)

