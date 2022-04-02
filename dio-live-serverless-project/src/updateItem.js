"use strict";

const AWS = require("aws-sdk");

const updateItem = (event) => {
    let {id} = event.pathParameters;

    const {receivedItem} = JSON.parse(event.body);

    const dynamoDB = new AWS.DynamoDB.DocumentClient();
    let item;

    try{
        const results = dynamoDB.scan({
            TableName: "ItemTable",
            Key: {id}
        }).promise();
        item = results.item;
        if(null != item) {
            await dynamoDB.update({
                TableName: "ItemTable",
                Key: {id},
                UpdateExpression: "set newItem = :newItem",
                ExpressionAttributeValue: {
                    ":newItem": newItem
                },
                ReturnValues: "ALL_NEW"
            }).promise();
        }
    } catch(error) {
        console.log(error);
    }

    return {
        statusCode: 200,
        body: JSON.stringify({
            msg: "Item updated"
        })
    };
};

module.exports = {
    handler: updateItem
}