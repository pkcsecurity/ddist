(ns ddist.core
  (:gen-class)
  (:require [clojure.set :as s]))

;; Takes [m] map and [s] string 
;; Returns [m] with [s] as key and frequency of [s] as the value 
(defn word-freq [m s]
  (update m s (fnil inc 0)))

(defn word-cnt-reduce [v]
  (reduce word-freq {} v))

;; Multiplies the intersection of the keys of two maps, then sum the results
(defn compute-inner [m1 m2]
  (let [kys (s/intersection (set (keys m1)) (set (keys m2)))]
    (reduce #(+ %1 (* (get m1 %2) (get m2 %2))) 0 kys)))

;; Perfomes document distance calculation
(defn calc-dist[m1 m2]
  (let [inner-product (compute-inner m1 m2)
        denom (Math/sqrt (* (compute-inner m2 m2) (compute-inner m1 m1)))]
    (Math/acos (/ inner-product denom))))

;; Takes strings [a] and [b]
;; Returns a number that indicates how similar the two are.
;;
;; acos(0) = 1.57079... = no similarity
;; acos(1) = 0 = identical
(defn dist [a b]
  (let [dict-a (word-cnt-reduce (clojure.string/split a #" "))
        dict-b (word-cnt-reduce (clojure.string/split b #" "))]
    (calc-dist dict-a dict-b)))
