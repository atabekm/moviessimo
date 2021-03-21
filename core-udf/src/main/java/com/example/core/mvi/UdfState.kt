package com.example.core.mvi

/**
 * This a parent interface from which all States should derive. State is a final point in
 * unidirectional state flow pattern (along with [UdfEffect]), where we bind the outcome of the state
 * to UI friendly fields.
 */
interface UdfState
