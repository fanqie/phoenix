package gov.sandia.phoenix.geometry

import java.text.DecimalFormat
import java.util.logging.Logger

import scala.math._

/**
 * Azimuth-Elevation class.
 * <p>
 * @author [[mailto:markbastian@gmail.com Mark Bastian]]
 */
case class AzEl(azimuth : Double, elevation : Double) {
  require(elevation >= -90 && elevation <= 90, "Elevation is " + elevation)

  def az(az : Double) = AzEl(az, elevation)
  def el(el : Double) = AzEl(azimuth, el)

  def interpolate(t : Double, that : AzEl) = AzEl(this.azimuth + t * (that.azimuth - this.azimuth),
      min(90, max(-90, this.elevation + t * (that.elevation - this.elevation))))

  def pretty(sigdigs : Int) = {
    val p = ("0." /: (0 to sigdigs)){ (a, _) => a + "#" }
    val f = new DecimalFormat(p)
    "Az, El : " + f.format(azimuth) + "\u00B0, " + f.format(elevation) + "\u00B0"
  }

  def toMatrix : RotationMatrix = toQuaternion.toMatrix

  /* The old way, which makes no sense, but agrees with STK
   * According to https://www.stk.com/resources/help/stk613/helpSystem/stk/sn-orientation.htm#azelorient
   * they measure the azimuth about +Z, which seems like crazy talk.
    val az = Quaternion.rotZ(toRadians(azimuth))
    val el = Quaternion.rotY(toRadians(90 - elevation))
    az * el
   */
  def toQuaternion = Degrees(-azimuth).rz * Degrees(elevation).ry * Degrees(-90).ry

  def toSEZ = Degrees(-azimuth).rz * Degrees(elevation).ry * Vector3(-1, 0, 0)
}