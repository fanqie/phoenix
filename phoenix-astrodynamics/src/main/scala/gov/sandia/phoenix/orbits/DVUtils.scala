/*
 * Copyright (c) 2016 Sandia Corporation. All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file epl-v10.html at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by the
 * terms of this license.
 * You must not remove this notice, or any other, from this software.
 *
 * Contributors:
 * - Mark Bastian: Original author.
 * - See Git logs.
 */

package gov.sandia.phoenix.orbits

import gov.sandia.phoenix.constants.WGS84
import gov.sandia.phoenix.elements.sv.ECIStateVector
import gov.sandia.phoenix.geometry.Vector3

import scala.math._

/**
 * Methods for determining delta vs
 */
object DVUtils {
  /**
   * Minimum velocity required to escape Earth orbit.
   */
  def minEscapeVelocity(eci : Vector3) = sqrt(2.0 * WGS84.GM / eci.mag)

  /**
   * Magnitude of velocity required to create an Earth centered circular orbit at a given eci location.
   * Note that the direction of this velocity must be perpendicular to the position.
   */
  def circularizingVelocity(eci : Vector3) = sqrt(WGS84.GM / eci.mag)

  /**
   * Compute the delta V required to circularize the orbit from this state. The
   * resulting orbit will have the current radius and be tangential to the
   * current velocity.
   */
  def circularizingDeltaV(state : ECIStateVector) =
    state.velocity.normalized * circularizingVelocity(state.position) - state.velocity

  /**
   * Compute the delta V required to escape Earth's gravity.
   */
  def minEscapeDeltaV(state : ECIStateVector) =
    state.velocity.normalized * minEscapeVelocity(state.position) - state.velocity

  /**
   * Delta-V required to change the semi-major axis to a. This is usually done
   * when the flight path angle is low so that other parameters are unaffected.
   */
  def axisDeltaV(state: ECIStateVector, a: Double) =
    state.velocity.normalized * sqrt(2.0 * WGS84.GM /state. position.mag - WGS84.GM / a) - state.velocity

  def rangeDeltaV(state: ECIStateVector, r: Double) = axisDeltaV(state, (r + state.position.mag) * 0.5)

}
