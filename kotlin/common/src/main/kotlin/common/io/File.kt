package common.io

import common.strings.nonBlankLines
import java.io.File
import java.io.FileNotFoundException

fun inputFile(filename: String): File {
  val pathname =
    object {}.javaClass.getResource("/input/$filename")?.file
      ?: throw FileNotFoundException("classpath:/input/$filename")
  return File(pathname)
}

fun File.readNonBlankLines(): List<String> =
  readLines().nonBlankLines()
