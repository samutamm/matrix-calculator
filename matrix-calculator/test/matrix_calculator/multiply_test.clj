(ns matrix_calculator.multiply-test
  (:require [clojure.test :refer :all]
            [matrix_calculator.multiply :refer :all]))

(def testcase [[1  2  3  4]
              [5  6  7  8]
              [9  10 11 12]
              [13 14 15 16]])
(def testcase2 [[1  2]
                [13 14]])

(def testcase3
  [[1 2 3 4 5]
   [2 3 4 5 6]
   [3 4 5 6 7]
   [4 5 6 7 8]
   [5 6 7 8 9]])

(deftest multiply.row-iteration
  (testing "if the row iteration returns an matrix with elements defined
    in the next function."
    (let [test-f (fn [a b] (+ (apply + a)(apply + b)))]
      (is (= (row-iteration test-f [[3 4][1 2]] [[5 6][2 3]])
             [[18 12][14 8]]))
      (is (= (row-iteration test-f [[1 2]] [[2 1]])
             [[6]])))))

(deftest multiply.matrix-multipcation.invalid-matrices
  (testing "that error will be thrown with matrices that cannot be multiplied."
    (is (thrown? Exception (matrix-multiplication [[1][2][3]] [[1 2]])))
    (is (thrown? Exception (matrix-multiplication [[1 2][3 4]] [[1 2][3 4][5 6]] )))
    (is (thrown? Exception (matrix-multiplication [1 2 3] [[1 2 3]] )))))

(deftest multiply.matrix-multipcation.valid-input
  (testing "that the function completes the same tasks as GNU Octave."
    (is (= (matrix-multiplication [[3 4 3][11 55 9][43 2 4]]
                                  [[98 5 1][6 7 6][50 19 3]])
          [[468 100 36][1858 611 368][4426 305 67]] ))
    (is (= (matrix-multiplication [[1.2 6.3]] [[9][2.7]])
           [[27.810]]))))

(deftest multiply.sub-matrix.invalid-params
  (testing "that the function do throw exception if the wanted submatrix is out
     of the bounds of the original matrix."
    (is (thrown? Exception (sub-matrix testcase 3 1 2)))
    (is (thrown? Exception (sub-matrix testcase 1 1 5)))))

(deftest multiply.sub-matrix.valid-params
  (testing "that the function returns submatrix specified in params."
    (is (= (sub-matrix testcase 0 0 (count testcase)) testcase))
    (is (= (sub-matrix testcase 3 3 1) [[16]]))))

(deftest multiply.concat-matrices.matrices-not-same-type
  (testing "that function throws error if the matrices are not same type."
    (is (thrown? Exception (concat-matrices testcase testcase testcase testcase2)))))

(deftest multiply.concat-matrices.matrices-are-same-type
  (testing "that function returns four time bigger matrix."
    (is (= (count (concat-matrices testcase testcase testcase testcase)) (* 2 (count testcase))))
    (is (= (concat-matrices testcase2 testcase2 testcase2 [[7 7][9 9]])  [[1 2 1 2][13 14 13 14][1 2 7 7][13 14 9 9]]))))

(deftest multiply.is-power-of-two?
  (testing "that function recognizes powers of two."
    (is (is-power-of-two? 256))
    (is (not (is-power-of-two? 17)))))

(deftest multiply.expand-matrix-by-one
  (testing "function works"
    (is (= (expand-matrix-by-one testcase2) [[1 2 0][13 14 0][0 0 0]]))))

(deftest multiply.find-next-power-of-two
  (testing "that function returns correct output."
    (is (= (find-next-power-of-two testcase2) testcase2))
    (is (= (find-next-power-of-two testcase) testcase))
    (is (= (find-next-power-of-two [[1 1 1 1 1]
                                    [1 1 1 1 1]
                                    [1 1 1 1 1]
                                    [1 1 1 1 1]
                                    [1 1 1 1 1]])
           [[1 1 1 1 1 0 0 0]
            [1 1 1 1 1 0 0 0]
            [1 1 1 1 1 0 0 0]
            [1 1 1 1 1 0 0 0]
            [1 1 1 1 1 0 0 0]
            [0 0 0 0 0 0 0 0]
            [0 0 0 0 0 0 0 0]
            [0 0 0 0 0 0 0 0]]))))

(deftest multiply.strassen
  (testing "that strassen returns correct values for matrices. Testcases are verified with GNU Octave"
    (is (= (strassen testcase3 testcase3)
           [[55    70    85   100   115]
            [70    90   110   130   150]
            [85   110   135   160   185]
            [100   130   160   190   220]
            [115   150   185   220   255]]))
    (is (= (strassen [[4]] [[3]]) [[12]] ))
    (is (= (strassen testcase testcase)
           [[90   100   110   120]
            [202   228   254   280]
            [314   356   398   440]
            [426   484   542   600]]))))
