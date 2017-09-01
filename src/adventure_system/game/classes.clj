(ns adventure-system.game.classes
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def ^:private
  data (edn/read-string (slurp (io/resource "world/classes.edn"))))

(defrecord Class
  [base specializations])

(defn get-class
  ([key]
   (get-class key (:classes data)))
  ([key classes]
   (first (filter #(= (:key %) key) classes))))

(defn get-specialization
  ([key]
   (get-specialization key (:specializations data)))
  ([key specs]
   (first (filter #(= (:key %) key) specs))))

