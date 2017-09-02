(ns adventure-system.db
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [korma.db :refer :all]
            [korma.core :refer :all]))

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
                   :as   player}]
               (-> player
                   (assoc :class (keyword class))
                   (assoc :specializations
                          (map keyword (string/split specs #",")))))))

(defn init []
  (default-connection connection)
  (select player))

(defn has-character [user]
  "STUB: check if there's an entry in the character table for this user"
  nil)

(defn create-character [type user]
  "STUB: create a new character of specified type for user"
  nil)
