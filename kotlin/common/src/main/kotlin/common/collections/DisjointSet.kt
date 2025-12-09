package common.collections

// see https://en.wikipedia.org/wiki/Disjoint-set_data_structure
class DisjointSet<T> {
  private val parents: MutableMap<T, T> = mutableMapOf()
  private val counts: MutableMap<T, Int> = mutableMapOf()

  fun setSizes(): List<Int> = counts.values.toList()

  fun countSets(): Int = counts.size

  fun addAll(elements: Collection<T>) = elements.forEach { add(it) }

  fun add(element: T) {
    if (element !in parents) {
      parents[element] = element
      counts[element] = 1
    }
  }

  // Note: Assumes element is always present
  fun find(element: T): T =
    if (parents.getValue(element) == element) element
    else find(parents.getValue(element)).also { parents[element] = it }

  // Note: Assumes left and right are always present
  fun union(left: T, right: T) {
    val leftRoot = find(left)
    val rightRoot = find(right)

    if (leftRoot != rightRoot) {
      if (counts.getValue(leftRoot) < counts.getValue(rightRoot)) {
        parents[rightRoot] = leftRoot
        counts[leftRoot] = counts.getValue(leftRoot) + counts.getValue(rightRoot)
        counts.remove(rightRoot)
      } else {
        parents[leftRoot] = rightRoot
        counts[rightRoot] = counts.getValue(rightRoot) + counts.getValue(leftRoot)
        counts.remove(leftRoot)
      }
    }
  }
}
