(ns adventure-bot.core
  (:gen-class)
  (:require [adventure-bot.db :as db]))

(defn init []
  (db/init))

(defn -main
  [& args]
  (init))
