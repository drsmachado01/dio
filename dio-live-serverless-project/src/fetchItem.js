"use strict";

const AWS = require("aws-sdk");

const fetchItem = async (event) => {
    let {id} = event.pathParameters;
    const dynamoDB = new AWS.DynamoDB.DocumentClient();
    let item;

    try{
        const results = dynamoDB.scan({
            TableName: "ItemTable",
            Key: {id}
        }).promise();
        item = results.item;
    } catch(error) {
        console.log(error);
    }
    return {
        statusCode: 200,
        body: JSON.stringify(item)
    };
}

module.exports = {
    handler: fetchItem
}