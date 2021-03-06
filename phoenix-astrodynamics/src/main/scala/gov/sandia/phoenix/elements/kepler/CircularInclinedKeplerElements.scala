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

package gov.sandia.phoenix.elements.kepler

import gov.sandia.phoenix.elements.kepler.KeplerElements._
import gov.sandia.phoenix.elements.sv.ECIStateVector
import gov.sandia.phoenix.geometry._

case class CircularInclinedKeplerElements(p : Double,
                                     inclination : Angle,
                                     OMEGA : Angle,
                                     u : Angle) extends KeplerElements {
  val e = 0.0
  val eccentricAnomaly = ECCENTRIC_ANOMALY(u.radians)
  val omega = Angle.NaN
  val nu = Angle.NaN
  val flightPathAngle = Angle.ZERO

  override lazy val apogee = new CircularInclinedKeplerElements(p, inclination, OMEGA, Angle.Pi).state
  override lazy val perigee = new CircularInclinedKeplerElements(p, inclination, OMEGA, Angle.ZERO).state
  //Should this be u or u - meanAnomaly?
  override lazy val ascendingNode = new CircularEquatorialKeplerElements(p, u.explement).state
  override lazy val descendingNode = new CircularEquatorialKeplerElements(p, u.supplement).state
//  override lazy val isAtAscendingNode = abs(nu + u) < 1.0E-6
//  override lazy val isAtDescendingNode = abs(Pi - nu + u) < 1.0E-6
  lazy val orientation = q(OMEGA, inclination, Angle.ZERO)
  
  override def state : ECIStateVector = RandV(p, e, inclination, OMEGA, Angle.ZERO, u)
  override def state(dt : Double) : ECIStateVector = new CircularInclinedKeplerElements(p, inclination, OMEGA, nu(dt)).state

  override def pretty = super.pretty +
  "\nright ascension: " + OMEGA.degrees + "\u00B0" +
  "\nargument of latitude: " + u.degrees + "\u00B0"
}
