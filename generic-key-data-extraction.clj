(def playlist
[{:title "Elephant", :artist "The White Stripes", :year 2003}
{:title "Helioself", :artist "Papas Fritas", :year 1997}
{:title "Stories from the City, Stories from the Sea",
:artist "PJ Harvey", :year 2000}
{:title "Buildings and Grounds", :artist "Papas Fritas", :year 2000}
{:title "Zen Rodeo", :artist "Mardi Gras BB", :year 2002}])

(defn generic-key-data-extraction
  [coll]
  (let [coll-keys (map keys coll)]
    (map (fn [item item-keys]
         (map (fn [k](k item))
              item-keys))
       playlist
       coll-keys)))

(generic-key-data-extraction playlist)


(defn summarize [{:keys [title artist year]}]
(str title " / " artist " / " year))

(map summarize playlist)