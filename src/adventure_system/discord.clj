(ns adventure-system.discord
  (:require [clj-discord.core :as clj-discord]))

(defonce token (.trim (slurp "token")))
(defn d100 [type data]
  (clj-discord/answer-command data "!d100" (str "Here you are a random number between 1 and 100: " (+ (rand-int 100) 1))))
(defn log-event [type data]
  (println "\nReceived: " type " -> " data))

(defn connect []
  (clj-discord/connect
    token
    {"MESSAGE_CREATE" [d100]
     "MESSAGE_UPDATE" [d100]
     ; "ALL_OTHER" [log-event] ; This generates massive chatter, disable when not fishing for events
     }
    true))