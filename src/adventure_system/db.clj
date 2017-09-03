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
  (entity-fields :id :username :name :class :specializations :gold)

  (transform (fn [{class :class
                   specs :specializations
                   left  :left
                   right :right
                   :as   player}]
               (-> player
                   (assoc :class (keyword class))
                   (assoc :specializations
                          (map keyword (string/split (or specs "") #",")))
                   (dissoc :left)
                   (dissoc :right)
                   (assoc :weapons {:left left :right right})))))


(defn init []
  (default-connection connection))

(defn get-player [username]
  (first (select player
           (where (= :username username)))))

(defn has-player [username]
  (first (select player
           (where (= :username username)))))

(defn- extract-user-data [[class spec & name-parts]]
  {:class (keyword class)
   :specialization (keyword spec)
   :name (string/replace (string/join " " name-parts) "\"" "")})

(defn- save-player-data [username data]
  (let [results (insert player
                  (values {:username username
                           :name (:name data)
                           :class (name (:class data))
                           :specializations (name (:specialization data))
                           :gold 0}))]
    ((keyword "last_insert_rowid()") results)))

(defn create-player [args username]
  "Create character and return their id"
  (let [data (extract-user-data (string/split args #" "))
        attrs (classes/base-attributes (:class data) (:specialization data))
        id (save-player-data username data)]
    (insert attributes (values (assoc attrs :players_id id)))
    (insert weapons (values {:players_id id}))
    (insert equipment (values {:players_id id}))
    id))

