(ns adventure-system.characters.classes
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def classes (edn/read-string
               (slurp (io/resource "world/classes.edn"))))
