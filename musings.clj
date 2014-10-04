;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.


(defn fibonacci
  [n]
  (loop
    [i 0
     f0 0
     f1 1
     f []]
       (if (= i n)
         f
         (recur (inc i) f1 (+ f0 f1) (conj f f1)))))

(fibonacci 0)

(fibonacci 1)

(fibonacci 2)

(fibonacci 3)


(fibonacci 7)

(fibonacci 8)

 (reduce )

  #(loop
    [i 0
     f0 0
     f1 1
     f []]
       (if (= i %)
         f
         (recur (inc i) f1 (+ f0 f1) (conj f f1))))


  ( #(take % (map second (iterate (fn [[x y]] [y (+ x y)]) [0 1]))) 3)

  (iterate (fn [[x y]] [y (+ x y)]) [0 1])

  (take 7 (iterate (fn [[x y]] [y (+ x y)]) [0 1]))

 (map last (take 7 (iterate (fn [[x y]] [y (+ x y)]) [0 1])))


 '(:A (:B (:D) (:E)) (:C (:F)))

 (odd? (count (map first (tree-seq next rest '(:a (:b nil nil) nil) ))))


 (odd? (count (map first (tree-seq next rest '(:a (:b nil nil)) ))))

 (odd? (count (map first (tree-seq next rest [1 nil [2 [3 nil nil] [4 nil nil]]] ))))

 (odd? (count (map first (tree-seq next rest [1 [2 nil nil] [3 nil nil] [4 nil nil]] ))))

 (odd? (count (map first (tree-seq next rest [1 [2 [3 [4 nil nil] nil] nil] nil] ))))



 (count '(:a (:b nil nil) nil))
 (count (flatten [1 [2 [3 [4 false nil] nil] nil] nil] ))

 (count (flatten [1 [2 [3 [4 nil nil] nil] nil] nil]))

 (count (flatten '(:a nil ())))


 (#(let[elements (flatten %)](and (odd?(count elements)) (not (some false? elements))))  '(:a (:b nil nil) nil))

 (complement (some false? (flatten [1 [2 [3 [4 nil nil] nil] nil] nil])))

   (take 5 ((fn[function parameter]
             (lazy-seq (apply function [parameter]))) (fn [x](* 2 x)) 1))


   (+ 2 2)



   (take 5((fn lazy-iterate
     [function parameter]
       (let [result (biginteger (apply function [parameter]))
              ]
      (cons result (lazy-seq (lazy-iterate function result))))) #(* 2 %) 1))


  ; (lazy-iterate #(* 2 %) 1)


   (take 10 (iterate #(* 2 %) 1))


   (take 100 (range))

   (take 10((fn lazy-iterate
     [function parameter]
       (let [p (- parameter 1)
             result (biginteger (apply function [p]))
              ]
      (cons (biginteger (apply function [parameter])) (lazy-seq (lazy-iterate function result))))) inc 0))


   (reduce (fn[e el](conj e (* el el))) [] [1 2 3 4 5 6 7 8 9] )
    (reduce #(conj % (* %2 %2)) [] [1 2 3 4 5 6 7 8 9] )








