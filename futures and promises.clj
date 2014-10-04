;; Futures

;; A future is a simple way to run code on a different thread, and it’s useful for long-running computations or blocking calls that can benefit from multithreading.
(defn long-calculation [num1 num2]
 (Thread/sleep 5000)
 (* num1 num2))

;; We have this slow-running function, let’s imagine you needed to run multiple such computations.
(defn long-run []
 (let [x (long-calculation 11 13)
 y (long-calculation 13 17)
 z (long-calculation 17 19)]
 (* x y z)))

(time (long-run)) ;; "Elapsed time: 14998.165 msecs"
                  ;; 10207769

;; long-run will benefit from being multithreaded. That’s where futures come in. The general form of a future is
(future & body)

;; It returns an object that will invoke body on a separate thread. The returned object can be queried for the return value of the body.
;; In case the computation hasn’t completed yet, the threading asking for the value will block. The result of the computation is cached, so subsequent queries for the value are immediate.
(defn fast-run []
 (let [x (future (long-calculation 11 13))
 y (future (long-calculation 13 17))
 z (future (long-calculation 17 19))]
 (* @x @y @z)))

(time (fast-run)) ;; "Elapsed time: 5000.078 msecs"
                  ;; 10207769

;; Futures are a painless way to get things to run on a different thread.
;;Here are a few future-related functions Clojure provides:
      ;; ■ future?—Checks to see if the object is a future, returns true if it is.
      ;; ■ future-done?—Returns true if the computation represented by this future object is completed.
      ;; ■ future-cancel—Attempts to cancel this future. If it has already started executing, it doesn’t do anything.
      ;; ■ future-cancelled?—Returns true if the future has been canceled.

;; Promises

;; A promise is an object that represents a commitment that a value will be delivered to it. You create one using the no-argument promise function:
(def p (promise))

;; In order to ask for the promised value, you can dereference it:
(def value (deref p))
@p

;; The way the value delivery system works is via the use of the deliver function. The general form of this function is
(deliver promise value)

;; Typically, this function is called from a different thread, so it’s a great way to communicate between threads.
;; The deref function (or the reader macro version of it) will block the calling thread if no value has been delivered to it yet.
;; The thread automatically unblocks when the value becomes available. The concept of promises finds a lot of use in things such as data-flow programming.
