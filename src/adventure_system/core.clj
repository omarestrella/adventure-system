(ns adventure-system.core
  (:gen-class)
  (:require [adventure-system.db :as db]
            [adventure-system.discord :as discord]
            [clojure.string :as str]
            [clojure.core.async :as async]))

(def character-create-help
  "```
Usage: /register <Class> <Specialization> \"Character Name\"
Class choices: Warrior, Mage, Rogue
Specializations:
    Warrior: Fighter, Knight, Paladin
    Mage: Enchanter, Battlemage, Cleric
    Rogue: Thief, Ranger, Assassin
```")

(def chat-interface
  {"/roll"     (fn [arg msg]
                 (let [range (. Integer parseInt (if (clojure.string/blank? arg) "20" arg))
                       roll-val (+ 1 (rand-int range))]
                   (discord/send-reply msg (str " roll (1-" range "): " roll-val))))

   "/help"     (fn [_ msg]
                 "STUB"
                 nil)

   "/register" (fn [args msg]
                 (let [username (discord/get-username msg)]
                   (if (db/has-character username)
                     (discord/send-reply msg "You already have a character")
                     (if-let [character (db/create-character args username)]
                       (discord/send-reply msg " Character created")
                       (discord/send-reply msg character-create-help)))))})

(defn -main
  [& args]
  (db/init)
  ; New async thread so execution continues
  (async/go
    (discord/connect chat-interface)))
