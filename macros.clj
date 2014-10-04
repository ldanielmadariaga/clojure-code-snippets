;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.
(defmacro unless [test then]
  (list 'if (list 'not test)
    then))

 (defn exhibits-oddity? [x]
   (unless (even? x)
       (str "Very odd, indeed!")))

 (exhibits-oddity? 3)
 (exhibits-oddity? 2)

 (macroexpand '(unless (even? x) (println "Very odd, indeed!")))

 ;; rewritting the macro with syntax quote (`) and unquotes (~)
 (defmacro unless2 [test then]
   `(if (not ~test)
      ~then))

 (defn exhibits-oddity2? [x]
   (unless2 (even? x)
       (str "Very odd, indeed two!")))

 (exhibits-oddity2? 3)
 (exhibits-oddity2? 2)

(defmacro failing-unless [test & exprs]
  `(if (not ~test)
     (do  ~exprs)))

(defn failing-exhibits-oddity? [x]
  (failing-unless (even? x)
          (println "Odd!")
          (println "Very odd!")))

(failing-exhibits-oddity? 3) ;;java.lang.NullPointerException: null

;;The NullPointerException is thrown because the encloses the functions with an extra pair of parenthesis mistaking the structure for a function call
(macroexpand-1 '(failing-unless  (even? x) (println "Odd!") (println "Very odd!")))

;; unquote-splicing to the rescue
(defmacro correct-unless [test & exprs]
  `(if (not ~test)
     (do  ~@exprs)))

(defn correct-exhibits-oddity? [x]
  (correct-unless (even? x)
          (println "Odd!")
          (println "Very odd!")))

(correct-exhibits-oddity? 3)

;; The usage of do we have just seen is a common convenience added to macros so they can accept multiple calls in their bodies


;; We get a failing macro because we are using a namespace-qualified name inside it
(defmacro def-logged-fn [fn-name args & body]
  `(defn ~fn-name ~args
     (let [now (System/currentTimeMillis)]
       (println "[" now "] Call to" (str (var ~fn-name)))
       ~@body)))


(def-logged-fn printname [name]
 (println "hi" name)) ;; clojure.lang.Compiler$CompilerException: java.lang.RuntimeException: Can't let qualified name: user/now, compiling

;; We confirm the issue with macroexpand-1 (notice user/now)
(macroexpand-1 '(def-logged-fn printname [name]
 (println "hi" name)))
;; (clojure.core/defn printname [name] (clojure.core/let [user/now (System/currentTimeMillis)]
;; (clojure.core/println "[" user/now "] Call to" (clojure.core/str (var printname))) (println "hi" name)))


(defn daily-report [arg] )

(def-logged-fn daily-report [the-day]
 ;; code to generate a report here
);; clojure.lang.Compiler$CompilerException: java.lang.RuntimeException: Can't let qualified name: user/ …

(let [now "2014-10-03"]
  (daily-report now))

;; auto-gensym to the rescue use as var# (reader macro version)
(defmacro def-logged-fn-gensym [fn-name args & body]
  `(defn ~fn-name ~args
     (let [now# (System/currentTimeMillis)]
       (println "[" now# "] Call to" (str (var ~fn-name)))
       ~@body)))

(def-logged-fn-gensym daily-report [the-day]
 ;; code to generate a report here
 (println "Reporting thingys")
)

(let [now "2014-10-03"]
  (daily-report now))

;; forget multiple arity and error checking :P
(defmacro infix
  [expr]
  (let [[left op right] expr]
    (list op left right)))

(infix (2 + 3))

(defmacro randomly
  [& exprs]
  (let [len (count exprs)
        index (rand-int len)
        conditions (map #(list '= index %) (range len))]
    `(cond ~@(interleave conditions exprs))))

(randomly (println "amit") (println "deepthi") (println "adi"))

(randomly (println "amit") (println "deepthi") (println "adi"))

(macroexpand-1 '(randomly  (println "amit")
                           (println "deepthi")
                           (println "adi")))
;; (clojure.core/cond (= 2 0) (println "amit")
                   ;; (= 2 1) (println "deepthi")
                   ;; (= 2 2) (println "adi"))

;;Much better!
(defmacro randomly-2 [& exprs]
 (nth exprs (rand-int (count exprs))))

;;(randomly-2 (println "Lenny") (println "Carl") (println "Moe"))

(defn bla []
  (randomly-2 (println "Lenny") (println "Carl") (println "Moe")))

(bla)

(defn bla2 []
  (randomly (println "amit") (println "deepthi") (println "adi")))

(bla2)

;; The randomly macros value is closed over when called from withing a fn therefore the same value will always be return

(defn check-credentials [username password]
 true)

(defn login-user
  [request]
  (let [username (:username request)
        password (:password request)]
    (if (check-credentials username password)
      (str "Welcome back " username ", " password " is correct!")
      (str "Login failed!"))))

(def request {:username "amit" :password "123456"})

(login-user request)

(defmacro defwebmethod [name args & exprs]
  `(defn ~name [{:keys ~args}]
     ~@exprs))

(defwebmethod login-user2 [username password]
  (if (check-credentials username password)
    (str "Welcome back " username ", " password " is still correct!")
    (str "Login failed!")))

(login-user2 request)


(defmacro assert-true [test-expr]
 (let [[operator lhs rhs] test-expr]
 `(let [lhsv# ~lhs rhsv# ~rhs ret# ~test-expr]
 (if-not ret#
 (throw (RuntimeException.
 (str '~lhs " is not " '~operator " " rhsv#)))
 true))))

(assert-true (< (* 2 4) (/ 18 2)))

(assert-true (= (* 2 4) (/ 16 2)))

(assert-true (>= (* 2 4) (/ 18 2))) ;; (* 2 4) is not >= 9  [Thrown class java.lang.RuntimeException]


(defmacro assert-true-with-more-checks [test-expr]
  (if-not (= 3 (count test-expr))
    (throw (RuntimeException.
            "Argument must be of the form
            (operator test-expr expected-expr)")))
  (if-not (some #{(first test-expr)} '(< > <= >= = not=))
    (throw (RuntimeException.
            "operator must be one of < > <= >= = not=")))
  (let [[operator lhs rhs] test-expr]
    `(let [lhsv# ~lhs rhsv# ~rhs ret# ~test-expr]
       (if-not ret#
         (throw (RuntimeException.
                 (str '~lhs " is not " '~operator " " rhsv#)))
         true))))


(assert-true-with-more-checks (>= (* 2 4) (/ 18 2) (+ 2 5))) ;; Argument must be of the form (operator test-expr expected-expr)  [Thrown class java.lang.RuntimeException]

(assert-true-with-more-checks (<> (* 2 4) (/ 16 2))) ;; operator must be one of < > <= >= = not=  [Thrown class java.lang.RuntimeException]













