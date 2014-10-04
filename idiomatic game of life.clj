;;Conway's Game of Life
;;Any live cell with fewer than two live neighbours dies, as if caused by under-population.
;;Any live cell with two or three live neighbours lives on to the next generation.
;;Any live cell with more than three live neighbours dies, as if by overcrowding.
;;Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

(defn empty-board
  "Creates a rectangular empty board of the specified width and height."
  [w h]
  (vec (repeat w (vec (repeat h nil)))))

(defn populate
  "Turns :on each of the cells specified as [y, x] coordinates."
  [board living-cells]
  (reduce (fn [board coordinates]
            (assoc-in board coordinates :on))
          board
          living-cells))

(defn neighbours
  [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1] :when (not= 0 dx dy)]
    [(+ dx x) (+ dy y)]))

(defn step
  "Yields the next state of the world."
  [cells]
  (set (for [[loc n] (frequencies (mapcat neighbours cells))
             :when (or (= n 3) (and (= n 2) (cells loc)))]
         loc)))

(->> (iterate step #{[2 0] [2 1] [2 2] [1 2] [0 1]})
(drop 8)
first
(populate (empty-board 6 6))
clojure.pprint/pprint)

(step #{[2 0] [2 1] [2 2] [1 2] [0 1]})
(populate (empty-board 6 6) (step #{[2 0] [2 1] [2 2] [1 2] [0 1]}))

 (mapcat neighbours #{[2 0] [2 1] [2 2] [1 2] [0 1]})

 (frequencies(mapcat neighbours #{[2 0] [2 1] [2 2] [1 2] [0 1]}))

(#{[2 0] [2 1] [2 2] [1 2] [0 1]} [2 1])

(#{[2 0] [2 1] [2 2] [1 2] [0 1]} [0 0])

(defn stepper
  "Returns a step function for Life-like cell automata.
  Neighbours takes a location and return a sequential collection of locations.
  survive? and birth? are predicates on the number of living neighbours."
  [neighbours birth? survive?]
  (fn [cells]
    (set (for [[loc n] (frequencies (mapcat neighbours cells))
               :when (if (cells loc) (survive? n) (birth? n))]
           loc))))

 ;;Our previous step example with stepper
 (stepper neighbours #{3} #{2 3})


