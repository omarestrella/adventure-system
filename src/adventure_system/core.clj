(ns adventure-system.core
  (:gen-class)
  (:require [adventure-system.db :as db]
            [adventure-system.discord :as discord]
            [clojure.string :as str]))

(def chat-interface
  {"/roll"     (fn [arg msg]
                 (let [range (. Integer parseInt (if (clojure.string/blank? arg) "20" arg))
                       roll-val (+ 1 (rand-int range))]
                   (discord/send-reply msg (str "roll (1-" range "): " roll-val))))

   "/help"     (fn [_ msg]
                 "STUB"
                 nil)

   "/register" (fn [args msg]
                 (let [user (discord/get-user msg)]
                   (if (db/has-character user)
                     (discord/send-reply msg "You already have a character")
                     (if-let [character (db/create-character args user)]
                       (discord/send-reply msg "Character created")
                       (discord/send-reply msg "usage: /register Warrior|Mage|Rogue")))))})

(defn -main
  [& args]
  (db/init)
  (discord/connect chat-interface))
