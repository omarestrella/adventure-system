(ns adventure-system.core
  (:gen-class)
  (:require [adventure-system.db :as db]
            [adventure-system.discord :as discord]
            [clojure.string :as str]))

(def chat-interface
  {"/d100" discord/d100
   "/d20"  discord/d20
   "/roll" discord/roll})

(defn -main
  [& args]
  (db/init)
  (discord/connect chat-interface))
