(ns adventure-system.discord
  (:require [clj-discord.core :as clj-discord])
  (:require [clojure.string :as str]))

(defonce token (.trim (slurp "token")))

(defn d100 [args data]
  (clj-discord/answer-command data "/d100" (str "Here you are a random number between 1 and 100: " (+ (rand-int 100) 1))))

(defn d20 [args data]
  (clj-discord/answer-command data "/d20" (str "roll (1-20): " (+ (rand-int 20) 1))))

(defn roll [args data]
  (let [range (. Integer parseInt args)
        roll-val (+ 1 (rand-int range))
        message (str "roll (1-" range "): " roll-val)
        channel (get data "channel_id")
        user (get (get data "author") "id")]
    (clj-discord/post-message-with-mention channel message user)))

(defn process-message [command-map]
  (fn [type data]
    (when-let [content (get data "content")]
      (let [[command rest] (str/split content #" " 2)]
        (when-let [executor (get command-map command)]
          (executor rest data))))))

(defn connect [command-map]
  (let [receiver [(process-message command-map)]]
    (clj-discord/connect token
                         {"MESSAGE_CREATE" receiver
                          "MESSAGE_UPDATE" receiver}
                         false)))
