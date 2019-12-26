package com.example.core.usf

/**
 * This a parent interface from which all States should derive. State is a final point in
 * unidirectional state flow pattern (along with [ViewEffect]), where we bind the outcome of [ViewResult]
 * to UI friendly fields.
 */
interface ViewState
