(ns adventure-system.discord
  (:require [clj-discord.core :as clj-discord])
  (:require [clojure.string :as str]))

(defonce token (.trim (slurp "token")))

(defn d100 [type data]
  (clj-discord/answer-command data "/d100" (str "Here you are a random number between 1 and 100: " (+ (rand-int 100) 1))))

(defn d20 [type data]
  (clj-discord/answer-command data "/d20" (str "1 and 20: " (+ (rand-int 20) 1))))

(defn roll [type data]
  (let [content (get data "content")
        parts (str/split content #" ")
        number (. Integer parseInt (get parts 1))
        range (+ 1 number)]
    (clj-discord/post-message-with-mention (get data "channel_id") (str "roll: " (rand-int range)) (get (get data "author") "id"))))

(defn parseMessage [type data]
  (let [content (get data "content")]
    (if content
      (let [[command rest] (str/split content #" " 2)]
        (case command
          "/d100" (d100 type data)
          "/d20" (d20 type data)
          "/roll" (roll type data)
          "default")))))

(defn log-event [type data]
  (println "\nReceived: " type " -> " data))

(defn connect []
  (let [commands [parseMessage]]
    (clj-discord/connect
      token
      {"MESSAGE_CREATE" commands
       "MESSAGE_UPDATE" commands}
      false)))
