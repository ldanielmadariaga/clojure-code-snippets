;; Anything you type in here will be executed
;; immediately with the results shown on the
;; right.

(defn fee-amount
  [percentage user]
  (float (* 0.01 percentage (:salary user))))

;; Messy to modify and add new conditions
(defn affiliarte-fee-cond
  [user]
  (cond
   (= :google.com (:referrer user)) (fee-amount 0.01 user)
   (= :mint.com (:referrer user)) (fee-amount 0.03 user)
   :default (fee-amount 0.02 user)))


;; With multimethods
(defmulti affiliate-fee :referrer)

(defmethod affiliate-fee :mint.com [user]
  (fee-amount 0.03 user))

(defmethod affiliate-fee :google.com [user]
  (fee-amount 0.01 user))

(defmethod affiliate-fee :default [user]
  (fee-amount 0.02 user))

(def user-mint {:login "rob" :referrer :mint.com :salary 1000000})

(def user-google {:login "kyle" :referrer :google.com :salary 90000})

(def user-yahoo {:login "celeste" :referrer :yahoo.com  :salary 70000})

(affiliate-fee user-mint)

(affiliate-fee user-google)

(affiliate-fee user-yahoo)

(defn profit-rating
  [user]
  (let [ratings [::bronze ::silver ::gold ::platinum]]
    (nth ratings (rand-int (count ratings)))))

(defn fee-category
  [user]
  [(:referrer user) (profit-rating user)])

(defmulti profit-based-affiliate-fee fee-category)

(defmethod profit-based-affiliate-fee [:mint.com ::bronze] [user]
  (fee-amount 0.03 user))

(defmethod profit-based-affiliate-fee [:mint.com ::silver] [user]
  (fee-amount 0.04 user))

(defmethod profit-based-affiliate-fee [:mint.com ::premier] [user]
  (fee-amount 0.05 user))

(defmethod profit-based-affiliate-fee [:google.com ::premier] [user]
  (fee-amount 0.03 user))

(defmethod profit-based-affiliate-fee :default [user]
  (fee-amount 0.02 user))

(derive ::bronze ::basic)
(derive ::silver ::basic)
(derive ::gold ::premier)
(derive ::platinum ::premier)








