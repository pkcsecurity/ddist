(ns ddist.core-test
  (:require [clojure.test :refer :all]
            [ddist.core :refer :all]))

(deftest dist-test 
  (testing "dist calculation"
    (is (= (dist "a b" "a b") 0.0))
    (is (= (dist "a b" "a a") 0.7853981633974484))
    (is (= (dist "the quick brown fox jumps and jumps pretty well" "the quick brown fox is a fox that knows how to fox") 1.1302856637901981))
    (is (= (dist "a" "b") 1.5707963267948966)))) 
