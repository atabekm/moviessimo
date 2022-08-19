package com.example.core.mvi

/**
 * This a parent interface from which all Effects should derive. Effect is a final point in
 * unidirectional state flow pattern (along with [MviState]), and it is used for non UI related stuff
 * like notifications, navigation, and etc.
 */
interface MviEffect
