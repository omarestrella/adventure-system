(ns adventure-system.game.classes
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def ^:private
  data (edn/read-string (slurp (io/resource "world/classes.edn"))))

(def classes (:classes data))
(def specializations (:specializations data))

(defn get-class
  ([key]
   (get-class key classes))
  ([key classes]
   (first (filter #(= (:key %) key) classes))))

(defn get-specialization
  ([key]
   (get-specialization key specializations))
  ([key specs]
   (first (filter #(= (:key %) key) specs))))

(defn base-attributes
  [class-key spec-key]
  (let [class (get-class class-key)
        specialization (get-specialization spec-key)
        stats (:stats class)
        mods (:modifiers specialization)]
    (reduce-kv (fn [m k v]
                 (assoc m k (+ v (get mods k 0))))
               {}
               stats)))

