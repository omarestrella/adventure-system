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

(declare player)

(defentity player
  (pk :id)
  (table :players)
  (entity-fields :player_id :username))

(defn init []
  (default-connection connection)
  (select player))

(defn has-character [user]
  "STUB: check if there's an entry in the character table for this user"
  nil)

(defn create-character [type user]
  "STUB: create a new character of specified type for user"
  nil)
