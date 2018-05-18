package com.udacity.capstone.injection;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Scope
@Documented
@Retention(value= RetentionPolicy.RUNTIME)
@interface Scope {}