(ns adventure-system.game.characters)

; Documentation purposes
(def character-format
  [:id
   :name
   :attributes [:strength :intelligence :dexterity :constitution :speed :luck]
   :inventory [:items :gold]
   :weapons [:left :right]
   :equipment [:helm :body :arms :pants]])

(defn get-attribute
  [char attr]
  (get-in char [:attributes attr]))

(defn get-attributes
  [char attrs]
  (filter identity (map #(get-attribute char %) attrs)))

(defn health
  "Based on constitution"
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