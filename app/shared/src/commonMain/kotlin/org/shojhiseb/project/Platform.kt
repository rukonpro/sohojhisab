package org.shojhiseb.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform