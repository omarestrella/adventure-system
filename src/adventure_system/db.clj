(ns adventure-system.db
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [korma.db :refer :all]
            [korma.core :refer :all]
            [adventure-system.game.classes :as classes]))

(def spec (sqlite3 {:db         (last (string/split
                                        (str (io/resource "sqlite.db"))
                                        #"file:"))
                    :make-pool? true}))

(defdb connection spec)

(declare player attributes items weapons equipment)

(defentity
  items
  (table :items)
  (entity-fields :key))

(defentity
  weapons
  (table :weapons)
  (entity-fields :left :right))

(defentity
  equipment
  (table :equipment)
  (entity-fields :head :body :arms :legs))

(defentity
  attributes
  (table :attributes)
  (entity-fields :strength :dexterity :intelligence
                 :constitution :speed :luck))

(defentity
  player
  (table :players)
  (has-one attributes)
  (has-one weapons)
  (has-one equipment)
  (has-many items)
  (entity-fields :username :name :class :specializations :gold)

  (transform (fn [{class :class
                   specs :specializations
                   left  :left
                   right :right
                   :as   player}]
               (-> player
                   (assoc :class (keyword class))
                   (assoc :specializations
                          (map keyword (string/split specs #",")))
                   (dissoc :left)
                   (dissoc :right)
                   (assoc :weapons {:left left :right right})))))


(defn init []
  (default-connection connection)
  (select player))

(defn has-character [username]
  (first (select player
           (where (= :username username)))))

(defn- extract-user-data [[class spec & name-parts]]
  {:class (keyword class)
   :specialization (keyword spec)
   :name (string/replace (string/join " " name-parts) "\"" "")})

(defn create-character [args username]
  "STUB: create a new character of specified type for user"
  (let [data (extract-user-data (string/split args #" "))
        attributes (classes/base-attributes (:class data) (:specialization data))]
    (+ 1 1)
    nil))
