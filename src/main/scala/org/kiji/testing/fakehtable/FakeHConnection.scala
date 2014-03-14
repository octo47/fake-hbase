/**
 * (c) Copyright 2013 WibiData, Inc.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kiji.testing.fakehtable

import org.apache.hadoop.hbase.client._
import java.util
import org.apache.hadoop.hbase._
import java.util.concurrent.ExecutorService
import org.apache.hadoop.hbase.client.coprocessor.Batch.Callback
import org.apache.hadoop.hbase.zookeeper.ZooKeeperWatcher
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.protobuf.generated.MasterProtos
import org.apache.hadoop.hbase.protobuf.generated.AdminProtos.AdminService
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos.ClientService

/**
 * A fake implementation of HConnection, useful only for support FakeHTable's usage in pools.
 * Implements calls to change its closeable state.  Everything else is a no-op and should not be
 * used.
 */
class FakeHConnection extends HConnection {
  var closed: Boolean = false

  override def close() {
    closed = true
  }

  override def isClosed(): Boolean = closed

  // ---------- Unimplemented methods below here -----------
  def abort(why: String, e: Throwable) = throw new UnsupportedOperationException

  def clearCaches(sn: String) = throw new UnsupportedOperationException

  def setRegionCachePrefetch(tableName: Array[Byte], enable: Boolean) =
     throw new UnsupportedOperationException

  def processBatchCallback[R](list: util.List[_ <: Row], tableName: Array[Byte], pool: ExecutorService, results: Array[AnyRef], callback: Callback[R]) =
      throw new UnsupportedOperationException

  def processBatch(actions: util.List[_ <: Row], tableName: Array[Byte], pool: ExecutorService, results: Array[AnyRef]) =
     throw new UnsupportedOperationException

  def clearRegionCache(tableName: Array[Byte]) = throw new UnsupportedOperationException

  def clearRegionCache() = throw new UnsupportedOperationException

  def getConfiguration: Configuration = throw new UnsupportedOperationException

  def getZooKeeperWatcher: ZooKeeperWatcher = throw new UnsupportedOperationException

  def getMaster: MasterProtos.MasterService.BlockingInterface = throw new UnsupportedOperationException

  def isMasterRunning: Boolean = throw new UnsupportedOperationException

  def isTableEnabled(tableName: Array[Byte]): Boolean = throw new UnsupportedOperationException

  def isTableDisabled(tableName: Array[Byte]): Boolean = throw new UnsupportedOperationException

  def isTableAvailable(tableName: Array[Byte]): Boolean = throw new UnsupportedOperationException

  def listTables(): Array[HTableDescriptor] = throw new UnsupportedOperationException

  def getHTableDescriptor(tableName: Array[Byte]): HTableDescriptor =
      throw new UnsupportedOperationException

  def locateRegion(tableName: Array[Byte], row: Array[Byte]): HRegionLocation =
      throw new UnsupportedOperationException

  def relocateRegion(tableName: Array[Byte], row: Array[Byte]): HRegionLocation =
      throw new UnsupportedOperationException

  def locateRegion(regionName: Array[Byte]): HRegionLocation =
      throw new UnsupportedOperationException

  def locateRegions(tableName: Array[Byte]): util.List[HRegionLocation] =
      throw new UnsupportedOperationException

  def getRegionLocation(tableName: Array[Byte], row: Array[Byte], reload: Boolean): HRegionLocation =
      throw new UnsupportedOperationException

  def getRegionCachePrefetch(tableName: Array[Byte]): Boolean =
      throw new UnsupportedOperationException

  def getCurrentNrHRS: Int = 0

  def getHTableDescriptors(tableNames: util.List[String]): Array[HTableDescriptor] =
      throw new UnsupportedOperationException

  def isAborted: Boolean = false

  override def isDeadServer(serverName: ServerName): Boolean = ???

  override def getKeepAliveMasterService = ???

  override def clearCaches(sn: ServerName): Unit = ???

  override def getHTableDescriptorsByTableName(tableNames: util.List[TableName]): Array[HTableDescriptor] = ???

  override def getRegionCachePrefetch(tableName: TableName): Boolean = ???

  override def setRegionCachePrefetch(tableName: TableName, enable: Boolean): Unit = ???

  override def processBatchCallback[R](list: util.List[_ <: Row], tableName: TableName, pool: ExecutorService, results: Array[AnyRef], callback: Callback[R]): Unit = ???

  override def processBatch(actions: util.List[_ <: Row], tableName: TableName, pool: ExecutorService, results: Array[AnyRef]): Unit = ???

  override def getRegionLocation(tableName: TableName, row: Array[Byte], reload: Boolean): HRegionLocation = ???

  override def getAdmin(serverName: ServerName, getMaster: Boolean): AdminService.BlockingInterface = ???

  override def getClient(serverName: ServerName): ClientService.BlockingInterface = ???

  override def getAdmin(serverName: ServerName): AdminService.BlockingInterface = ???

  override def locateRegions(tableName: Array[Byte], useCache: Boolean, offlined: Boolean): util.List[HRegionLocation] = ???

  override def locateRegions(tableName: TableName, useCache: Boolean, offlined: Boolean): util.List[HRegionLocation] = ???

  override def locateRegions(tableName: TableName): util.List[HRegionLocation] = ???

  override def updateCachedLocations(tableName: Array[Byte], rowkey: Array[Byte], exception: scala.Any, source: HRegionLocation): Unit = ???

  override def updateCachedLocations(tableName: TableName, rowkey: Array[Byte], exception: scala.Any, source: HRegionLocation): Unit = ???

  override def relocateRegion(tableName: TableName, row: Array[Byte]): HRegionLocation = ???

  override def deleteCachedRegionLocation(location: HRegionLocation): Unit = ???

  override def clearRegionCache(tableName: TableName): Unit = ???

  override def locateRegion(tableName: TableName, row: Array[Byte]): HRegionLocation = ???

  override def getHTableDescriptor(tableName: TableName): HTableDescriptor = ???

  override def listTableNames(): Array[TableName] = ???

  override def getTableNames: Array[String] = ???

  override def isTableAvailable(tableName: Array[Byte], splitKeys: Array[Array[Byte]]): Boolean = ???

  override def isTableAvailable(tableName: TableName, splitKeys: Array[Array[Byte]]): Boolean = ???

  override def isTableAvailable(tableName: TableName): Boolean = ???

  override def isTableDisabled(tableName: TableName): Boolean = ???

  override def isTableEnabled(tableName: TableName): Boolean = ???

  override def getTable(tableName: TableName, pool: ExecutorService): HTableInterface = ???

  override def getTable(tableName: Array[Byte], pool: ExecutorService): HTableInterface = ???

  override def getTable(tableName: String, pool: ExecutorService): HTableInterface = ???

  override def getTable(tableName: TableName): HTableInterface = ???

  override def getTable(tableName: Array[Byte]): HTableInterface = ???

  override def getTable(tableName: String): HTableInterface = ???
}
