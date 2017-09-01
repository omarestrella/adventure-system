(ns adventure-system.game.characters)

; Documentation purposes
(def character-format
  [:id
   :name
   :attributes
   :inventory [:items :gold]
   :weapons [:left :right]
   :equipment [:helm :body :arms :pants]])

(defn health
  "Based on constitution"
  [char]
  nil)

(defn damage
  "Based on stat corresponding to weapon type (str, int, dex)
  and the given weapon stats"
  []
  nil)

(defn defense
  "Based on player strength, cons, armor, and modifiers"
  [player]
  nil)