;; Watching for mutation

;; Sometimes it’s useful to add an event listener that gets notified when the value of a stateful construct changes.
;; add-watch  allows you to register a regular Clojure function as a “watcher” against any kind of reference.
;; When the value of the reference changes, the watcher function is run.
;; The watcher must be a function of four arguments: a key to identify the watcher, the reference it’s being registered against, the old value of the reference, and finally, the new value of the reference.

(def adi (atom 0))
(defn on-change [the-key the-ref old-value new-value]
 (println "Hey, seeing change from" old-value "to" new-value))

(add-watch adi :adi-watcher on-change)

@adi ;; 0
(swap! adi inc) ;; Hey, seeing change from 0 to 1
                ;; 1

;; It’s also possible to remove a watch if it’s no longer required.
(remove-watch adi :adi-watcher)

