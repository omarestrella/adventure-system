(ns adventure-system.discord
  (:require [clj-discord.core :as clj-discord])
  (:require [clojure.string :as str]))

(defonce token (.trim (slurp "token")))

(defn get-user [msg-data]
  (get (get msg-data "author") "id"))

(defn get-channel [msg-data]
  (get msg-data "channel_id"))

(defn send-reply [msg-data reply]
  "send a reply to a user message in the same channel"
  (let [user (get-user msg-data)
        channel (get-channel msg-data)]
    (clj-discord/post-message-with-mention channel reply user)))

(defn process-message [command-map]
  "create processor function for messages that can delegate to the appropriate command function"
  (fn [msg-type msg-data]
    (when-let [content (get msg-data "content")]
      (let [[command args] (str/split content #" " 2)]
        (when-let [executor (get command-map command)]
          (executor args msg-data))))))

(defn connect [command-map]
  "connect to discord with a given set of commands configured"
  (let [receiver [(process-message command-map)]]
    (clj-discord/connect token
                         {"MESSAGE_CREATE" receiver
                          "MESSAGE_UPDATE" receiver}
                         false)))
