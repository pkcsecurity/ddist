(ns ddist.core
  (:gen-class))

; given a vector of strings
; return a map with string frequency
; key is the string
; value is the number of occurances of that string in the map 
(defn word-freq [m v]
    (update m v (fnil inc 0)))

; go through a vector of words and put them into a map with word freq
(defn word-cnt-reduce [v]
  (reduce word-freq {} v))

; multiply the union of two maps together, then sum the results
(defn compute-inner [m1 m2]
  (let [kys (keys (select-keys m1 (keys m2)))]
    (reduce + (map #(* (get m1 %) (get m2 %)) kys))))

; calc distance
(defn calc-dist[m1 m2]
  (let [inner-product (compute-inner m1 m2)
        denom (Math/sqrt (* (compute-inner m2 m2) (compute-inner m1 m1)))]
    (println (Math/acos (/ inner-product denom)))))

(defn dist [a b]
  (let [dict-a (word-cnt-reduce (clojure.string/split a #" "))
        dict-b (word-cnt-reduce (clojure.string/split b #" "))]
    (calc-dist dict-a dict-b)))
