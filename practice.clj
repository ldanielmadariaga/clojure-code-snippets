;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.

(mapcat list [1 2 3] [1 2 3])

([1 1 1 1])

(mapcat list [1 2 3] [])

(interleave [1 2 3 4 ] [5])

(map  read-string (re-seq #"\d" (str (* 99 9))))


(#(= (take (/ (count %) 2) %) (reverse (drop (/ (count %) 2) %))) '(1 1 3 3 1 1))

(#(= (take (if (zero? (rem (count %) 2)) (/ (count %) 2) (- (/ (count %) 2) 1)) %) (reverse (drop (/ (count %) 2) %))) "racecar")

(reverse (drop (/ (count "racecar") 2) "racecar"))
(take (/ (count "racecar") 2) "racecar")
(if (zero? (rem (count "racecar") 2)) (count [1 2 3]) (- (count [1 2 3]) 1))

(#(take (if (zero? (rem (count %) 2)) (/ (count %) 2) (- (/ (count %) 2) 1)) %) "racecar")
(def x java.lang.Class)
;(= (class x) x)
(fn [x](= (class x) x) Class)

 (let [[a c] [+ (range 3)]] (apply a c))
  (let [[[a c] b] [[+ 1] 2]] (a c b))
 (let [[a c] [inc 2]] (a c))


 (def bla [00])

 (filter (complement nil?) (reductions  (fn [x y](if (not= x y) val)) [] [1 1 2 3 3 2 2 3]))
  (filter (complement nil?) (reductions  #(if (not= %1 %2) %2) [[1 2] [1 2] [3 4] [1 2]]))
;   (filter (complement nil?) (reduce  #(if (not= %1 %2) %2) "Leeeeeerrroyyy"))
 (filter  nil? [1 2 3 4 5 6 7])

 (let [e []]
   (reduce
   ; #(str "1: " %1 " 2: " %& ) "Leeeeeerrroyyy"))
     #(if (= %1 %2)
        (cons %& e)
        (conj  e %1)) "Leeeeeerrroyyy"))

(nth [1 2 3 4 5] (+ 1 (.indexOf [1 2 3 4 5] 2)))


(def s "asd asd asd asd asd asd www www www www www")
(reduce #(do (println "%1:" %1 " %2:" %2)(assoc %1 %2 (inc (%1 %2 0))))
        {}
        (re-seq #"\w+" s))

(#(reduce
   (fn [e x]
     (if (not= (last e) x)
        (conj e x)
      e))
 []
 "Leeeeeerrroyyy"))




(def input [1 2 3 4 4 5 6 7 8 9])
(reduce (fn [i pair]
          (condp every? pair
            even? (inc i)
            odd?  (dec i)
            i))
        0 (partition 2 1 input))

(def sds [])
(cons 2 sds)


(map
    (fn [x](if true
       (do
         (println x e)
         (concat "pito" [x]))))
 [1 2 2 2 3 4 5])
(apply str '(1 2 3 4 5 6))


(= (apply str (#(reduce (fn [e x] (if (not= (last e) x) (conj e x) e))[] %) "Leeeeeerrroyyy")) "Leroy")


(apply str "Leroy")

(partition-by identity [1 1 2 1 1 1 3 3])
(#(butlast(interleave %2 (repeat %)))  0 [1 2 3])


((fn[f c](reduce #(conj % (f %2)) [] c)) inc [2 3 4 5 6])

(#(doall (reduce (fn[x y](conj x (% y))) [] %2)) inc [2 3 4 5 6])



;(->> ((fn[f c](reduce #(conj % (f %2)) [] c)) inc (range))
;        (drop (dec 1000000))
;        (take 2))

;(= [1000000 1000001]
;(->> (#(lazy-seq(reduce (fn[x y](conj x (% y))) [] %2)) inc (range))
;        (drop (dec 1000000))
;        (take 2)))


(#(concat (drop % %2)(take % %2)) 2 [1 2 3 4 5])
(#(let [c (cond (< % 0) (mod % (count %2)) (< (count %2) %) (mod % (count %2)) :else %)](concat (drop c %2) (take c %2))) -2 [1 2 3 4 5])

#(let [c (cond (< % 0) (+ (count %2) %)(< (count %2) %)(- % (count %2)):else % )](concat (drop c %2) (take c %2)))



(fn[i c](cond
   (< i (-(count c))) ()
 ;  (< % 0) (+ (count %2) %)
  ;   (< (count %2) %)(- % (count %2))
     :else i )
   -4 [:a :b :c] )

(< -4 (-(count [1 2 3])))

(complement (-(count [1 2 3])))


(mod 4 3)
(mod 4 3)
(rem 4 3)
(quot 77 3)

(mod -4 3)
(quot 3 -4)
(+ -4 (* 2(count [1 2 3])))