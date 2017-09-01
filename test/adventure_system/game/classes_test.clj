(ns adventure-system.game.classes_test
  (:require [clojure.test :refer :all]
            [adventure-system.game.classes :refer :all]))

(def test-data {:classes [{:key :warrior
                           :value "warrior"}
                          {:key :mage
                           :value "mage"}]
                :specializations [{:key :tester
                                   :value "tester"}
                                  {:key :dev
                                   :value "dev"}]})

(deftest classes
  (testing "get-class returns class based on key lookup"
    (let [data (get-class :warrior (:classes test-data))]
      (is (= (:value data) "warrior"))))

  (testing "get-specialization returns class based on key lookup"
    (let [data (get-specialization :tester (:specializations test-data))]
      (is (= (:value data) "tester")))))
