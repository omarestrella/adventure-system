(ns adventure-system.core
  (:gen-class)
  (:require [adventure-system.db :as db]
            [adventure-system.discord :as discord]
            [adventure-system.game.characters :as characters]
            [clojure.string :as str]
            [clojure.core.async :as async]))

(def chat-interface
  {"/roll"     (fn [arg msg]
                 (let [range (. Integer parseInt (if (clojure.string/blank? arg) "20" arg))
                       roll-val (+ 1 (rand-int range))]
                   (discord/send-reply msg (str " roll (1-" range "): " roll-val))))

   "/help"     (fn [_ msg]
                 "STUB"
                 nil)

   "/register" characters/create-character})

(defn -main
  [& args]
  (db/init)
  ; New async thread so execution continues
  (async/go
    (discord/connect chat-interface)))
