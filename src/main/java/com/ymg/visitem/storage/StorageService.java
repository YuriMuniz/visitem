package com.ymg.visitem.storage;
/*
 * Copyright (c) 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.google.api.client.http.InputStreamContent;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;

/**
 * Main class for the Cloud Storage JSON API sample.
 *
 * Demonstrates how to make an authenticated API call using the Google Cloud Storage API client
 * library for java, with Application Default Credentials.
 */
public class StorageService {
  
	public static final String IMG_DEFAULT_NAME = "/pedido_default.jpg";
	public static final String API_NAME = "https://storage.googleapis.com/";
  public static final String BUCKET_NAME = "bubbly-monument";
  public static final String PROJECT_NAME = "bubbly-monument";
	
//	public static final String IMG_DEFAULT_NAME = "produt_image_default.jpg";
//	public static final String API_NAME = "https://storage.googleapis.com/";
//  public static final String BUCKET_NAME = "avian-axis-124414.appspot.com";
//  public static final String PROJECT_NAME = "avian-axis-124414";
  
  
  // [START list_bucket]
  /**
   * Fetch a list of the objects within the given bucket.
   *
   * @param bucketName the name of the bucket to list.
   * @return a list of the contents of the specified bucket.
   */
  public static List<StorageObject> listBucket()
      throws IOException, GeneralSecurityException {
    Storage client = StorageFactory.getService();
    Storage.Objects.List listRequest = client.objects().list(BUCKET_NAME);

    List<StorageObject> results = new ArrayList<StorageObject>();
    Objects objects;

    // Iterate through each page of results, and add them to our results list.
    do {
      objects = listRequest.execute();
      // Add the items in this page of results to the list we'll return.
      results.addAll(objects.getItems());

      // Get the next page, in the next iteration of this loop.
      listRequest.setPageToken(objects.getNextPageToken());
    } while (null != objects.getNextPageToken());

    return results;
  }
  // [END list_bucket]

  // [START get_bucket]
  /**
   * Fetches the metadata for the given bucket.
   *
   * @param bucketName the name of the bucket to get metadata about.
   * @return a Bucket containing the bucket's metadata.
   */
  public static Bucket getBucket() throws IOException, GeneralSecurityException {
    Storage client = StorageFactory.getService();

    Storage.Buckets.Get bucketRequest = client.buckets().get(BUCKET_NAME);
    // Fetch the full set of the bucket's properties (e.g. include the ACLs in the response)
    bucketRequest.setProjection("full");
    return bucketRequest.execute();
  }
  // [END get_bucket]

  // [START upload_stream]
  /**
   * Uploads data to an object in a bucket.
   *
   * @param name the name of the destination object.
   * @param contentType the MIME type of the data.
   * @param stream the data - for instance, you can use a FileInputStream to upload a file.
   * @param bucketName the name of the bucket to create the object in.
   */
  public static void uploadStream(
      String name, String contentType, InputStream stream)
      throws IOException, GeneralSecurityException {
    InputStreamContent contentStream = new InputStreamContent(contentType, stream);
    StorageObject objectMetadata = new StorageObject()
        // Set the destination object name
        .setName(name)
        // Set the access control list to publicly read-only
        .setAcl(Arrays.asList(
            new ObjectAccessControl().setEntity("allUsers").setRole("READER")));

    // Do the insert
    
    Storage client = StorageFactory.getService();
    Storage.Objects.Insert insertRequest = client.objects().insert(
    		BUCKET_NAME, objectMetadata, contentStream);

    insertRequest.execute();
  }
  // [END upload_stream]

  // [START delete_object]
  /**
   * Deletes an object in a bucket.
   *
   * @param path the path to the object to delete.
   * @param bucketName the bucket the object is contained in.
   */
  public static void deleteObject(String path)
      throws IOException, GeneralSecurityException {
    Storage client = StorageFactory.getService();
    client.objects().delete(BUCKET_NAME, path).execute();
  }
}