(ns adventure-system.game.characters
  (:require [adventure-system.db :as db]
            [adventure-system.discord :as discord]))

; Documentation purposes
(def character-format
  [:id
   :name
   :attributes [:strength :intelligence :dexterity :constitution :speed :luck]
   :inventory [:items :gold]
   :weapons [:left :right]
   :equipment [:helm :body :arms :pants]])

(def character-create-help
  "```
Usage: /register <Class> <Specialization> \"Character Name\"
Class choices: Warrior, Mage, Rogue
Specializations:
    Warrior: Fighter, Knight, Paladin
    Mage: Enchanter, Battlemage, Cleric
    Rogue: Thief, Ranger, Assassin
```")

(defn welcome-message
  [player]
  (format " Welcome to the world of [Insert World Name], %s.
  You find yourself waking up in the local pub after a long night of drinking
  with your good friend Walter. You stumble out of the pub, the morning light
  blinding your hungover eyes. You look around and take stock of the rat-hole town
  Walter dragged you to with promises of splendor and infamy. Now he is nowhere to
  be found. Do you want to ask around for him?"
    (:name player)))

(defn create-character [args msg]
  (let [username (discord/get-username msg)]
    (if (db/has-player username)
      (discord/send-reply msg " You already have a character")
      (if-let [id (db/create-player args username)]
        (discord/send-reply msg (welcome-message (db/get-player username)))
        (discord/send-reply msg character-create-help)))))

(defn get-attribute
  [char attr]
  (get-in char [:attributes attr]))

(defn get-attributes
  [char attrs]
  (filter identity (map #(get-attribute char %) attrs)))

(defn health
  "Based on constitution"
  []
  (* 6.3 (get-attribute char :constitution)))

(defn damage
  "Based on stat corresponding to weapon type (str, int, dex)
  and the given weapon stats"
  [char weapon]
  nil)

(defn defense
  "Based on player strength, cons, armor, and modifiers"
  [player]
  nil)