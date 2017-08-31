(ns adventure-system.core
  (:gen-class)
  (:require [adventure-system.db :as db]))

(defn init []
  (db/init))

(defn -main
  [& args]
  (init))
