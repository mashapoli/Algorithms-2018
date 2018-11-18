package lesson3

import org.junit.jupiter.api.Tag
import kotlin.test.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BinaryTreeTest {
    private fun testAdd(create: () -> CheckableSortedSet<Int>) {
        val tree = create()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    @Tag("Example")
    fun testAddKotlin() {
        testAdd { createKotlinTree() }
    }

    @Test
    @Tag("Example")
    fun testAddJava() {
        testAdd { createJavaTree() }
    }

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    private fun <T : Comparable<T>> createKotlinTree(): CheckableSortedSet<T> = KtBinaryTree()

    private fun testRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            binarySet.remove(toRemove)
            println("Removing $toRemove from $list")
            assertEquals<SortedSet<*>>(treeSet, binarySet.toSortedSet(), "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(inn, element in binarySet,
                        "$element should be ${if (inn) "in" else "not in"} tree")
            }
            assertTrue(binarySet.checkInvariant())
        }
    }
    private fun testRemoveFromEmptyTree(create: () -> CheckableSortedSet<Int>) {
        val binarySet = create();
        assertFalse(binarySet.remove(1))
        assertEquals<Set<*>>(emptySet<Int>(), binarySet)
        assertEquals(0, binarySet.size)
    }

    @Test
    fun testRemoveFromEmptyTreeJava() {
        testRemoveFromEmptyTree { createJavaTree() }
    }

    private fun testRemoveNonExistentElement(create: () -> CheckableSortedSet<Int>) {
        val binarySet = create();
        binarySet += 2;
        binarySet += 1;
        binarySet += 3;
        assertFalse(binarySet.remove(4))
        assertEquals<SortedSet<*>>(sortedSetOf<Int>(1, 2, 3), binarySet)
        assertEquals(3, binarySet.size)
    }
    @Test
    fun testRemoveNonExistentElementJava() {
        testRemoveNonExistentElement { createJavaTree() }
    }

    private fun testRemoveFromEmpty(create: () -> CheckableSortedSet<Int>) {
        val binarySet = create();
        binarySet += 2;
        binarySet += 1;
        binarySet += 3;
        assertFalse(binarySet.remove(null))
        assertEquals<SortedSet<*>>(sortedSetOf<Int>(1, 2, 3), binarySet)
        assertEquals(3, binarySet.size)

    }
    @Test
    fun testRemoveFromEmptyJava() {
        testRemoveFromEmpty { createJavaTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveKotlin() {
        testRemove { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveJava() {
        testRemove { createJavaTree() }
    }

    private fun testIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next())
            }
        }
    }

    private fun testIteratorNull(create: () -> CheckableSortedSet<Int>) {
        val binarySet = create()
        val binaryIt = binarySet.iterator()
        assertFalse(binaryIt.hasNext())
        try {
            binaryIt.next()
            assertTrue(false, "Should throw exception")
        } catch (e: NoSuchElementException) {

        }
    }
    @Test
    fun testIteratorNullJava() {
        testIteratorNull { createJavaTree() }
    }

    private fun testIteratorOverflow(create: () -> CheckableSortedSet<Int>) {
        val binarySet = create()
        val binaryIt = binarySet.iterator()
        binarySet +=2
        binarySet +=1
        binarySet +=3
        try {
            binaryIt.next()
            assertTrue(false, "Should throw exception")
        } catch (e: NoSuchElementException) {}
    }
    @Test
    fun testIteratorOverflowJava() {
        testIteratorOverflow { createJavaTree() }
    }


    @Test
    @Tag("Normal")
    fun testIteratorKotlin() {
        testIterator { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testIteratorJava() {
        testIterator { createJavaTree() }
    }

    private fun testIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            while (iterator.hasNext()) {
                val element = iterator.next()
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(inn, element in binarySet,
                        "$element should be ${if (inn) "in" else "not in"} tree")
            }
            assertTrue(binarySet.checkInvariant())
        }
    }

    private fun testIteratorRemove1(create: () -> CheckableSortedSet<Int>) {
        val binarySet = create()
        binarySet += 2
        binarySet += 1
        binarySet += 3
        val iterator = binarySet.iterator()
        iterator.next()
        iterator.remove()
        val element = iterator.next()
        assertEquals(3, element)

    }
    @Test
    fun testIteratorRemoveJava1() {
        testIteratorRemove1 { createJavaTree() }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveKotlin() {
        testIteratorRemove { createKotlinTree() }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveJava() {
        testIteratorRemove { createJavaTree() }
    }
}