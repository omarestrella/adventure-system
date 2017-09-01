(ns adventure-system.core
  (:gen-class)
  (:require [adventure-system.db :as db])
  (:require [adventure-system.discord :as discord]))

(defn init []
  (db/init))

(defn -main
  [& args]
  (init)
  (discord/connect))
