package com.udacity.capstone.injection;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by christosdemetriou on 18/04/2018.
 */

@Scope
@Documented
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Scope
{
}