package com.sliidepoc.ui.utils

/**
 * Because it's used in combination with Savable option from a UI screen,
 * we must use ENUM instead of SEALED because only Bundle elements are compatible with
 * @link [canBeSaved] method from Saver.save jetpack compose class.
 *
 * @author Hagău Ioan
 * @since 2022.01.28
 */
enum class DataLoadingStates {
    SUCCESS,
    ERROR,
    LOADING,
    START
}
