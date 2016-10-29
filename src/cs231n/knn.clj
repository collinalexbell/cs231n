(ns cs231n.knn
  (:require
   [quil.core :as q]
   [cs231n.img-utils :as img-utils])
  (:use clojure.core.matrix))

(set-current-implementation :vectorz)

(def cifar-dir "resources/cifar-10-batches-bin")
(def cifar (img-utils/load-all-cifar cifar-dir))

(def classes ["plane", "car", "bird", "cat", "deer", "dog", "frog", "horse", "ship", "truck"])

(defn draw-img [img]
  (defn draw-img-setup [])
  (defn draw-img-draw []
    (doall
     (map-indexed
      (fn [c-index column]
        (doall
         (map-indexed
          (fn [r-index rgb]
            (q/set-pixel  c-index r-index (apply q/color rgb)))
          column)))
      (transpose img))))
  (q/defsketch img
    :title "A display img"
    :setup draw-img-setup
    :draw draw-img-draw
    :size [32 32]))













