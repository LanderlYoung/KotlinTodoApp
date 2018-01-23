package io.github.landerlyoung.awesometodo.test

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-23
 * Time:   00:06
 * Life with Passion, Code with Creativity.
 * </pre>
 */

data class Show(inline val showId: String?)

data class ShowInfo(inline val show: Show?)

data class GetShowInfo(inline val showInfo: ShowInfo?)
