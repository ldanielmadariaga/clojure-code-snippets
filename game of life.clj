;;Any live cell with fewer than two live neighbours dies, as if caused by under-population.
;;Any live cell with two or three live neighbours lives on to the next generation.
;;Any live cell with more than three live neighbours dies, as if by overcrowding.
;;Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

(defn empty-board
  "Creates a rectangular empty board of the specified width and height."
  [w h]
  (vec (repeat w (vec (repeat h nil)))))

(empty-board 2 2)

(defn populate
  "Turns :on each of the cells specified as [y, x] coordinates."
  [board living-cells]
  (reduce (fn [board coordinates]
            (assoc-in board coordinates :on))
          board
          living-cells))

(def glider (populate (empty-board 6 6) #{[2 0] [2 1] [2 2] [1 2] [0 1]}))

(clojure.pprint/pprint glider)

(defn neighbours
  [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1] :when (not= 0 dx dy)]
    [(+ dx x) (+ dy y)]))

(neighbours [2 2])

(defn count-neighbours
  [board loc]
  (count (filter #(get-in board %) (neighbours loc))))

(count-neighbours glider [2 2])


(defn filter-neighbours
  [board loc]
  (filter #(get-in board %) (neighbours loc)))

(filter-neighbours glider [2 2])
(filter-neighbours glider [0 0])

(defn indexed-step
  "Yields the next state of the board, using indices to determine neighbours, liveness, etc."
  [board]
  (let [w (count board)
        h (count (first board))]
    (loop [new-board board, x 0, y 0]
      (cond
       (>= x w) new-board
       (>= y h) (recur new-board (inc x) 0)
       :else
       (let [new-liveness
             (case (count-neighbours board [x y])
               2 (get-in board [x y])
               3 :on
               nil)]
         (recur (assoc-in new-board [x y] new-liveness) x (inc y)))))))


(-> (iterate indexed-step glider) (nth 8) clojure.pprint/pprint)

;; remove loop by reduce over a range
(defn indexed-step2
  [board]
  (let [w (count board)
        h (count (first board))]
    (reduce
     (fn [new-board x]
       (reduce
        (fn [new-board y]
          (let [new-liveness
                (case (count-neighbours board [x y])
                  2 (get-in board [x y])
                  3 :on
                  nil)]
            (assoc-in new-board [x y] new-liveness)))
        board (for [x (range h) y (range w)] [x y]))))))

;; collapsing nested reductions

(defn indexed-step3
  [board]
  (let [w (count board)
        h (count (first board))]
    (reduce
     (fn [new-board [x y]]
       (let [new-liveness
             (case (count-neighbours board [x y])
               2 (get-in board [x y])
               3 :on
               nil)]
         (assoc-in new-board [x y] new-liveness)))
     board (for [x (range h), y (range w)] [x y]))))


(partition 3 1 [2001] (range 5))

(defn window0
  "Returns a lazy sequence of 3-item windows centered around each item of coll."
  [coll]
  (partition 3 1 (concat [nil] coll [nil])))

(window [[0 1 2] [1 2 3] [2 3 4]])

(defn cell-block0
  "Creates sequences of 3x3 windows from a triple of 3 sequences."
  [[left mid right]]
   (window (map vector (or left (repeat nil)) mid (or right (repeat nil)))))

;; Simplifying code by giving an optional pad argument to window

(defn window
  "Returns a lazy sequence of 3-item windows centered around each item of coll, padded as necessary with pad or nil."
  ([coll] (window nil coll))
  ([pad coll] (partition 3 1 (concat [pad] coll [pad]))))

(defn cell-block
  "Creates sequences of 3x3 windows from a triple of 3 sequences."
  [[left mid right]]
   (window (map vector left mid right)))

(cell-block [[0 1 2] [1 2 3] [2 3 4]])

(defn block
  "Creates sequences of 3x3 windows from a triple of 3 sequences."
  [[left mid right]]
    (map vector left mid right))


(block [[5 6 7 ] [1 2 3] [9 10 11]])

(cell-block [[5 6 7 ] [1 2 3] [9 10 11]])

(defn liveness
  "Returns the liveness (nil or :on) of the center cell for the next step."
  [block]
  (let [ [_[_ center _]_] block]
    (case (- (count (filter #{:on} (apply concat block)))
             (if (= :on center) 1 0))
      2 center
      3 :on
      nil)))

(defn- step-row
  "Yields the next state of the center row."
  [rows-triple]
  (vec (map liveness (cell-block rows-triple))))

(defn index-free-step
  "Yields the next state of the board."
  [board]
  (vec (map step-row (window (repeat nil) board))))

(= (nth (iterate indexed-step glider) 8)
(nth (iterate index-free-step glider) 8))

(window "nothin'" glider)
(cell-block (window "nothin'" glider))
(vec (map liveness (cell-block (window "nothin'" glider))))





























